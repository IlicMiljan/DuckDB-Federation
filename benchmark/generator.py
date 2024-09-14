# Command should be run from root directory of the project
#
# docker run -it --rm --name data-generator-script \
#   -v "$(pwd)":/usr/src/script \
#   -w /usr/src/script \
#   python:3 /bin/bash -c "pip install faker pymysql && python benchmark/generator.py"

import pymysql
from faker import Faker
from datetime import datetime, timedelta
import random
from pymysql.cursors import DictCursor
import uuid
import time
import logging

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# Define configuration for the MySQL instance
db_config = {
    'host': 'mysql',
    'user': 'root',
    'password': '',
    'charset': 'utf8mb4',
    'cursorclass': DictCursor
}

# Define databases
databases = {
    'store_database': ['orders', 'order_items'],
    'user_database': ['users'],
    'activity_database': ['user_activity'],
    'inventory_database': ['products']
}

fake = Faker()

def get_connection(database=None):
    config = db_config.copy()
    if database:
        config['database'] = database
    for _ in range(3):
        try:
            return pymysql.connect(**config)
        except pymysql.Error as e:
            logging.error(f"Error connecting to database {database}: {e}")
            time.sleep(1)
    raise Exception(f"Failed to connect to database {database} after 3 attempts")

def random_date(start_date, end_date):
    time_between = end_date - start_date
    days_between = time_between.days
    random_number_of_days = random.randrange(days_between)
    return start_date + timedelta(days=random_number_of_days)

def create_databases_and_tables():
    conn = get_connection()
    cursor = conn.cursor()

    for db, tables in databases.items():
        cursor.execute(f"CREATE DATABASE IF NOT EXISTS {db}")
        cursor.execute(f"USE {db}")

        if db == 'store_database':
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS orders (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    order_date DATE,
                    total_amount DECIMAL(10, 2)
                )
            """)
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS order_items (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    order_id INT,
                    product_id INT,
                    quantity INT,
                    price DECIMAL(10, 2),
                    FOREIGN KEY (order_id) REFERENCES orders(id)
                )
            """)
        elif db == 'user_database':
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    email VARCHAR(100) UNIQUE,
                    registration_date DATE,
                    last_login_date DATE,
                    is_active BOOLEAN
                )
            """)
        elif db == 'activity_database':
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS user_activity (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    activity_type VARCHAR(50),
                    activity_date DATE
                )
            """)
        elif db == 'inventory_database':
            cursor.execute("""
                CREATE TABLE IF NOT EXISTS products (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    product_name VARCHAR(100),
                    category VARCHAR(50),
                    price DECIMAL(10, 2)
                )
            """)

    conn.commit()
    cursor.close()
    conn.close()
    logging.info("Databases and tables created or already exist.")

def insert_data(database, table_name, data, columns):
    conn = get_connection(database)
    cursor = conn.cursor()
    try:
        placeholders = ', '.join(['%s'] * len(columns))
        sql = f"INSERT INTO {table_name} ({', '.join(columns)}) VALUES ({placeholders})"
        cursor.executemany(sql, data)
        conn.commit()
        logging.info(f"{cursor.rowcount} records inserted into {database}.{table_name}")
    except Exception as e:
        conn.rollback()
        logging.error(f"Error inserting data into {database}.{table_name}: {e}")
    finally:
        cursor.close()
        conn.close()

def generate_users(num_users):
    users = []
    start_date = datetime(2020, 1, 1)
    end_date = datetime.now()
    for _ in range(num_users):
        users.append((
            fake.first_name(),
            fake.last_name(),
            f"{uuid.uuid4().hex}@example.com",
            random_date(start_date, end_date).strftime('%Y-%m-%d'),
            random_date(start_date, end_date).strftime('%Y-%m-%d'),
            random.choice([True, False])
        ))
    return users

def generate_products(num_products):
    products = []
    categories = ['Electronics', 'Clothing', 'Books', 'Home & Garden', 'Sports']
    adjectives = ['Premium', 'Deluxe', 'Essential', 'Compact', 'Advanced', 'Smart', 'Eco-friendly']
    nouns = ['Widget', 'Gadget', 'Tool', 'Device', 'System', 'Kit', 'Set']

    for _ in range(num_products):
        product_name = f"{random.choice(adjectives)} {random.choice(nouns)} {fake.color_name().capitalize()}"
        products.append((
            product_name,
            random.choice(categories),
            round(random.uniform(10, 1000), 2)
        ))
    return products

def generate_orders(num_orders, user_ids):
    orders = []
    start_date = datetime(2020, 1, 1)
    end_date = datetime.now()
    for _ in range(num_orders):
        orders.append((
            random.choice(user_ids),
            random_date(start_date, end_date).strftime('%Y-%m-%d'),
            round(random.uniform(10, 1000), 2)
        ))
    return orders

def generate_order_items(num_items, order_ids, product_ids):
    order_items = []
    for _ in range(num_items):
        order_items.append((
            random.choice(order_ids),
            random.choice(product_ids),
            random.randint(1, 5),
            round(random.uniform(10, 200), 2)
        ))
    return order_items

def generate_user_activities(num_activities, user_ids):
    activities = []
    activity_types = ['login', 'logout', 'view_product', 'add_to_cart', 'remove_from_cart']
    start_date = datetime(2020, 1, 1)
    end_date = datetime.now()
    for _ in range(num_activities):
        activities.append((
            random.choice(user_ids),
            random.choice(activity_types),
            random_date(start_date, end_date).strftime('%Y-%m-%d')
        ))
    return activities

def populate_data(num_users, num_products, num_orders, num_order_items, num_activities):
    # Insert users
    users = generate_users(num_users)
    insert_data('user_database', 'users', users, ['first_name', 'last_name', 'email', 'registration_date', 'last_login_date', 'is_active'])

    # Insert products
    products = generate_products(num_products)
    insert_data('inventory_database', 'products', products, ['product_name', 'category', 'price'])

    # Get user and product IDs
    conn_user = get_connection('user_database')
    cursor_user = conn_user.cursor()
    cursor_user.execute("SELECT id FROM users")
    user_ids = [row['id'] for row in cursor_user.fetchall()]
    cursor_user.close()
    conn_user.close()

    conn_inventory = get_connection('inventory_database')
    cursor_inventory = conn_inventory.cursor()
    cursor_inventory.execute("SELECT id FROM products")
    product_ids = [row['id'] for row in cursor_inventory.fetchall()]
    cursor_inventory.close()
    conn_inventory.close()

    # Insert orders
    orders = generate_orders(num_orders, user_ids)
    insert_data('store_database', 'orders', orders, ['user_id', 'order_date', 'total_amount'])

    # Get order IDs
    conn_store = get_connection('store_database')
    cursor_store = conn_store.cursor()
    cursor_store.execute("SELECT id FROM orders")
    order_ids = [row['id'] for row in cursor_store.fetchall()]
    cursor_store.close()
    conn_store.close()

    # Insert order items
    order_items = generate_order_items(num_order_items, order_ids, product_ids)
    insert_data('store_database', 'order_items', order_items, ['order_id', 'product_id', 'quantity', 'price'])

    # Insert user activities
    activities = generate_user_activities(num_activities, user_ids)
    insert_data('activity_database', 'user_activity', activities, ['user_id', 'activity_type', 'activity_date'])

try:
    create_databases_and_tables()
    populate_data(1000000, 10000, 2500000, 5000000, 15000000)
except Exception as e:
    logging.error(f"An error occurred: {e}")

logging.info("Data insertion completed.")
