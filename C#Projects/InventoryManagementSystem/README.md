## Inventory Management System

Role-based web app for managing products and categories built with C#, ASP.NET Core MVC, Entity Framework Core, ASP.NET Core Identity, and SQL Server.
I implemented authentication/authorization, admin-only features, category & product management, and custom profile fields as well as testing, debugging and UI.

Done in team of three with:
Evgenii Baldin: https://github.com/SnikeZ
Eduard Kosenko: https://github.com/gbc-ekos

## Features

- Auth & Roles (ASP.NET Core Identity)
- Roles: Admin (full access), Manager (manage inventory), Staff (view, limited actions)
- Role-aware navbar using a UserRole ViewComponent
- Inventory
- Categories: create, edit, delete
- Products: create (Admin-only), edit, delete, list, search
- Extended ApplicationUser with DateOfBirth, PhoneNumber, ProfilePicture
- Data & Migrations
- EF Core code-first, with migrations and seed data (roles + optional admin)

## Tech Stack

.NET 8/9 (ASP.NET Core MVC)
EF Core
Identity 
Razor Views, Tag Helpers, View Components
SQL Server
Bootstrap for styling

