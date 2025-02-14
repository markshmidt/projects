-- Clear existing data
TRUNCATE TABLE "Products" CASCADE;
TRUNCATE TABLE "Categories" CASCADE;

-- Reset sequences
ALTER SEQUENCE "Category_Id_seq" RESTART WITH 1;
ALTER SEQUENCE "Products_Id_seq" RESTART WITH 1;

-- Insert categories
INSERT INTO "Categories" ("Name") VALUES
('Electronics'),
('Home & Kitchen'),
('Books'),
('Sports & Outdoors'),
('Fashion');

-- Insert products for Electronics (CategoryId = 1)
INSERT INTO "Products" ("Name", "Description", "Price", "Quantity", "CategoryId") VALUES
('Smartphone Pro', 'Latest flagship smartphone with 6.7" display', 999.99, 50, 1),
('Laptop Elite', '15" laptop with high-performance specs', 1299.99, 30, 1),
('Wireless Earbuds', 'True wireless earbuds with noise cancellation', 199.99, 100, 1),
('4K Smart TV', '55" 4K Ultra HD Smart LED TV', 699.99, 25, 1),
('Gaming Console', 'Next-gen gaming console with 1TB storage', 499.99, 45, 1),
('Digital Camera', 'Mirrorless camera with 24MP sensor', 799.99, 20, 1),
('Smartwatch', 'Fitness tracking smartwatch with GPS', 299.99, 60, 1),
('Tablet Pro', '11" tablet with retina display', 599.99, 40, 1),
('Bluetooth Speaker', 'Portable wireless speaker with 20h battery', 79.99, 75, 1),
('Power Bank', '20000mAh portable charger with fast charging', 49.99, 100, 1);

-- Insert products for Home & Kitchen (CategoryId = 2)
INSERT INTO "Products" ("Name", "Description", "Price", "Quantity", "CategoryId") VALUES
('Coffee Maker', 'Programmable coffee maker with 12-cup capacity', 79.99, 40, 2),
('Air Fryer', 'Digital air fryer with multiple cooking modes', 129.99, 35, 2),
('Blender Set', 'High-speed blender with multiple attachments', 159.99, 30, 2),
('Toaster Oven', 'Convection toaster oven with digital controls', 89.99, 45, 2),
('Food Processor', '8-cup food processor with multiple blades', 119.99, 25, 2),
('Stand Mixer', 'Professional stand mixer with 5qt bowl', 299.99, 20, 2),
('Cookware Set', '10-piece non-stick cookware set', 199.99, 30, 2),
('Rice Cooker', 'Digital rice cooker with steamer function', 69.99, 50, 2),
('Knife Set', '15-piece kitchen knife set with block', 149.99, 35, 2),
('Food Storage Set', '24-piece glass food storage container set', 39.99, 60, 2);

-- Insert products for Books (CategoryId = 3)
INSERT INTO "Products" ("Name", "Description", "Price", "Quantity", "CategoryId") VALUES
('Programming Guide', 'Comprehensive guide to modern programming', 49.99, 100, 3),
('Science Fiction Novel', 'Bestselling sci-fi novel of the year', 24.99, 150, 3),
('Cookbook Collection', 'International recipes from master chefs', 34.99, 10, 3),
('Business Strategy', 'Modern business management strategies', 39.99, 70, 3),
('Self-Help Book', 'Guide to personal development and growth', 19.99, 120, 3),
('History Book', 'Illustrated world history encyclopedia', 59.99, 60, 3),
('Art Collection', 'Contemporary art and artists showcase', 79.99, 40, 3),
('Philosophy Text', 'Introduction to philosophical thinking', 29.99, 90, 3),
('Biography', 'Biography of influential personalities', 27.99, 110, 3),
('Technical Manual', 'Technical reference guide for professionals', 44.99, 75, 3);

-- Insert products for Sports & Outdoors (CategoryId = 4)
INSERT INTO "Products" ("Name", "Description", "Price", "Quantity", "CategoryId") VALUES
('Yoga Mat', 'Non-slip exercise yoga mat with carrier', 29.99, 100, 4),
('Dumbbells Set', '5-25lb adjustable dumbbells set', 199.99, 40, 4),
('Tennis Racket', 'Professional tennis racket with case', 159.99, 30, 4),
('Basketball', 'Official size indoor/outdoor basketball', 29.99, 80, 4),
('Camping Tent', '4-person waterproof camping tent', 199.99, 25, 4),
('Hiking Backpack', '40L waterproof hiking backpack', 89.99, 45, 4),
('Fitness Tracker', 'Advanced fitness activity tracker', 79.99, 60, 4),
('Soccer Ball', 'Professional soccer ball size 5', 39.99, 70, 4),
('Cycling Helmet', 'Adjustable bike helmet with LED light', 49.99, 55, 4),
('Jump Rope', 'Adjustable speed jump rope', 19.99, 120, 4);

-- Insert products for Fashion (CategoryId = 5)
INSERT INTO "Products" ("Name", "Description", "Price", "Quantity", "CategoryId") VALUES
('Leather Wallet', 'Genuine leather bifold wallet', 49.99, 100, 5),
('Sunglasses', 'Polarized UV protection sunglasses', 89.99, 75, 5),
('Watch Classic', 'Classic analog watch with leather strap', 199.99, 10, 5),
('Backpack', 'Stylish water-resistant backpack', 69.99, 60, 5),
('Running Shoes', 'Lightweight running shoes with cushioning', 129.99, 50, 5),
('Winter Jacket', 'Waterproof insulated winter jacket', 179.99, 35, 5),
('Dress Shirt', 'Cotton blend long-sleeve dress shirt', 59.99, 80, 5),
('Jeans', 'Classic fit denim jeans', 79.99, 90, 5),
('Belt', 'Genuine leather dress belt', 39.99, 70, 5),
('Travel Bag', 'Durable weekend travel duffel bag', 89.99, 45, 5);
