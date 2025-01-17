# E-commerce API Documentation

## Authentication APIs

### POST /api/auth/register
Register a new user account.
```json
// Request
{
  "email": "user@example.com",
  "username": "johndoe",
  "password": "securepassword",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890"
}

// Response - 201 Created
{
  "id": "uuid",
  "email": "user@example.com",
  "username": "johndoe",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "createdAt": "2024-01-17T10:00:00Z"
}
```

### POST /api/auth/login
Login with user credentials.
```json
// Request
{
  "username": "johndoe",
  "password": "securepassword"
}

// Response - 200 OK
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 3600
}
```

## User APIs

### GET /api/users/profile
Get current user profile.
```json
// Response - 200 OK
{
  "id": "uuid",
  "email": "user@example.com",
  "username": "johndoe",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "avatar": "url/to/avatar.jpg",
  "isVerified": true,
  "addresses": [
    {
      "id": "uuid",
      "fullName": "John Doe",
      "phone": "+1234567890",
      "addressLine1": "123 Main St",
      "city": "New York",
      "country": "USA",
      "isDefault": true
    }
  ]
}
```

### PUT /api/users/profile
Update user profile.
```json
// Request
{
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "avatar": "url/to/new-avatar.jpg"
}
```

## Product APIs

### GET /api/products
Get list of products with filtering and pagination.
```json
// Request
GET /api/products?category=electronics&minPrice=100&maxPrice=1000&page=1&size=10

// Response - 200 OK
{
  "items": [
    {
      "id": "uuid",
      "sku": "PROD-001",
      "name": "iPhone 13",
      "slug": "iphone-13",
      "description": "Latest iPhone model",
      "price": 999.99,
      "stock": 50,
      "mainImage": "url/to/image.jpg",
      "images": ["url1", "url2"],
      "categories": [
        {
          "id": "uuid",
          "name": "Electronics",
          "slug": "electronics"
        }
      ]
    }
  ],
  "totalItems": 100,
  "totalPages": 10,
  "currentPage": 1
}
```

### GET /api/products/{id}
Get product details by ID.
```json
// Response - 200 OK
{
  "id": "uuid",
  "sku": "PROD-001",
  "name": "iPhone 13",
  "slug": "iphone-13",
  "description": "Latest iPhone model",
  "price": 999.99,
  "stock": 50,
  "mainImage": "url/to/image.jpg",
  "images": ["url1", "url2"],
  "categories": [
    {
      "id": "uuid",
      "name": "Electronics",
      "slug": "electronics"
    }
  ]
}
```

## Cart APIs

### GET /api/cart
Get current user's cart.
```json
// Response - 200 OK
{
  "id": "uuid",
  "items": [
    {
      "id": "uuid",
      "product": {
        "id": "uuid",
        "name": "iPhone 13",
        "price": 999.99,
        "mainImage": "url/to/image.jpg"
      },
      "quantity": 1,
      "price": 999.99
    }
  ],
  "totalAmount": 999.99,
  "expiryDate": "2024-01-24T10:00:00Z"
}
```

### POST /api/cart/items
Add item to cart.
```json
// Request
{
  "productId": "uuid",
  "quantity": 1
}

// Response - 200 OK
{
  "id": "uuid",
  "totalAmount": 999.99,
  "items": [...]
}
```

## Order APIs

### POST /api/orders
Create new order from cart.
```json
// Request
{
  "addressId": "uuid",
  "paymentMethod": "CREDIT_CARD",
  "notes": "Please deliver in the morning"
}

// Response - 201 Created
{
  "id": "uuid",
  "orderNumber": "ORD-2024-0001",
  "status": "PENDING",
  "paymentStatus": "PENDING",
  "items": [
    {
      "productName": "iPhone 13",
      "quantity": 1,
      "price": 999.99,
      "subtotal": 999.99
    }
  ],
  "subtotal": 999.99,
  "tax": 90.00,
  "shippingFee": 10.00,
  "totalAmount": 1099.99,
  "createdAt": "2024-01-17T10:00:00Z"
}
```

### GET /api/orders
Get list of user's orders.
```json
// Request
GET /api/orders?status=PENDING&page=1&size=10

// Response - 200 OK
{
  "items": [
    {
      "id": "uuid",
      "orderNumber": "ORD-2024-0001",
      "status": "PENDING",
      "paymentStatus": "PENDING",
      "totalAmount": 1099.99,
      "createdAt": "2024-01-17T10:00:00Z"
    }
  ],
  "totalItems": 50,
  "totalPages": 5,
  "currentPage": 1
}
```

## Category APIs

### GET /api/categories
Get categories tree.
```json
// Response - 200 OK
{
  "items": [
    {
      "id": "uuid",
      "name": "Electronics",
      "slug": "electronics",
      "children": [
        {
          "id": "uuid",
          "name": "Smartphones",
          "slug": "smartphones"
        }
      ]
    }
  ]
}
```

## Admin APIs

### POST /api/admin/products
Create new product (Admin only).
```json
// Request
{
  "sku": "PROD-001",
  "name": "iPhone 13",
  "slug": "iphone-13",
  "description": "Latest iPhone model",
  "price": 999.99,
  "stock": 50,
  "mainImage": "url/to/image.jpg",
  "images": ["url1", "url2"],
  "categoryIds": ["uuid1", "uuid2"]
}
```

### PUT /api/admin/orders/{id}/status
Update order status (Admin only).
```json
// Request
{
  "status": "PROCESSING"
}
```
