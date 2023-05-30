# makanan-online-api
pembuat @LRistiana <br/>
Port : 8067

## Spesifikasi Program
API ini dapat menghandle empat request method yaitu
* GET
* POST
* PUT
* DELETE<br/>
Status Code 200 Untuk Sukses <br/>
Status Code 400 Untuk Invalid request

## Struktur Program
berikut ini merupakan class yang digunakan di makanan-online-api
### server
class ini berfungsi sebagai entry point dan mengontrol alur request dari api<br/>
di dalam class ini terdapat beberapa class pendukung seperti
* Handler
class ini akan menjadi entry poin
* GetHandler
class ini untuk memetakan request dari request method GET
* PutHandler
class ini untuk memetakan request dari request method PUT
* Post Handler
class ini untuk memetakan request dari request method POST
* DeleteHandler
class ini untuk memetakan request dari request method DELETE

### DatabaseManager
class ini digunakan untuk konektivitas dengan database SQLLite

### OrdersController
class ini digunakan untuk menghandle request dari entitas Orders

### ProductsController
class ini digunakan untuk menghandle request dari entitas Products

### UsersController
class ini digunakan untuk menghandle request dari entitas Users


## Menggunakan Program
### Request Method GET
#### users
get all user
>http://localhost:8067/users
result :
```[
    {
        "firstName": "Liangga",
        "lastName": "Ristiana",
        "phoneNumber": "082147473737",
        "id": 1,
        "type": "Buyer",
        "email": "liangga4@gmail.com"
    }, ...
```
get user by id
>http://localhost:8067/users/3
result :
```[
    {
        "firstName": "kadekmsaasasaed",
        "lastName": "maria",
        "phoneNumber": "0939393",
        "id": 3,
        "type": "Seller",
        "Adresses": [
            {
                "province": "bali",
                "city": "badung",
                "postcode": "12311",
                "type": "seller",
                "line1": "manjada"
            }
        ],
        "email": "komang@gmail"
    }
]
```
get user by type
>http://localhost:8067/users?type=Buyer
result :
```[
    {
        "firstName": "Liangga",
        "lastName": "Ristiana",
        "phoneNumber": "082147473737",
        "id": 1,
        "type": "Buyer",
        "email": "liangga4@gmail.com"
    },
    {
        "firstName": "dinba",
        "lastName": "asd",
        "phoneNumber": "082145322344",
        "id": 5,
        "type": "Buyer",
        "email": "lasiasd@asdasd"
    }, ...
 ```
get user by condition
> http://localhost:8067/users?field=id&cond=largerEqual&val=5
result
```[
    {
        "firstName": "dinba",
        "lastName": "asd",
        "phoneNumber": "082145322344",
        "id": 5,
        "type": "Buyer",
        "email": "lasiasd@asdasd"
    },
    {
        "firstName": "awun",
        "lastName": "isaj",
        "phoneNumber": "0939393",
        "id": 6,
        "type": "Seller",
        "email": "komang@gmail"
    }, ...
```
get user products
> http://localhost:8067/users/3/products
result :
```[
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 1,
        "title": "Es teh",
        "stock": "50"
    },
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 3,
        "title": "es gula",
        "stock": "40"
    }, ...
```

get user orders
> http://localhost:8067/users/2/orders
result :
```[
    {
        "note": 2,
        "isPaid": 1,
        "total": 2,
        "discount": 11,
        "id": 1,
        "buyer": 2
    }
]
```
get user reviews
> http://localhost:8067/users/2/reviews
result :
```[
    {
        "star": 5,
        "description": "bagus",
        "order": 1
    }
]
```

### products
get all products
> http://localhost:8067/products
result : 
```[
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 1,
        "title": "Es teh",
        "stock": "50"
    },
```
get products by id
> http://localhost:8067/products/3
result :
```[
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 3,
        "title": "es gula",
        "stock": "40"
    }
]
```
get products by condition
> http://localhost:8067/products?field=stock&cond=larger&val=30
result : 
```
[
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 1,
        "title": "Es teh",
        "stock": "50"
    },
    {
        "seller": "3",
        "price": "5000",
        "description": "dingin",
        "id": 3,
        "title": "es gula",
        "stock": "40"
    }, ...
```

### orders
get all orders
> http://localhost:8067/orders
result :
```
[
    {
        "note": 2,
        "isPaid": 1,
        "total": 2,
        "discount": 11,
        "id": 1,
        "buyer": 2
    }, ...
```
get order by id
> http://localhost:8067/orders/1
result : 
```[
    {
        "note": 2,
        "isPaid": 1,
        "total": 2,
        "phoneNumber": "0939393",
        "reviews": [
            {
                "star": 5,
                "description": "bagus"
            }
        ],
        "discount": 11,
        "id": 1,
        "Order_detail": [
            {
                "product": "Es teh",
                "quantity": 2,
                "price": 2000
            },
            {
                "product": "Es teh",
                "quantity": 1,
                "price": 5000
            }
        ],
        "email": "komang@gmail",
        "buyer": "awun isaj"
    }
]
```
### request method Post
format path
> http://localhost:8067/{entitas}
<br/>format JSON users :
```
{
    "firstName" : "VALUE",
    "lastName" : "VALUE",
    "email" : "VALUE",
    "phoneNumber" : "VALUE",
    "type" : "VALUE"
}
```
<br/>format JSON products :
```
{
    "seller" : VALUE,
    "title" : "VALUE",
    "description" : "VALUE",
    "price" : "VALUE",
    "stock" : VALUE
}
```
<br/>format JSON orders :
```
{
    "note": VALUE,
    "isPaid": VALUE,
    "total": VALUE,
    "discount": VALUE,
    "buyer": VALUE
}
```
### request method Put
format path
> http://localhost:8067/{entitas}/{id}
<br/>format JSON users :
```
{
    "id" : VALUE,
    "firstName" : "VALUE",
    "lastName" : "VALUE",
    "email" : "VALUE",
    "phoneNumber" : "VALUE",
    "type" : "VALUE"
}
```
<br/>format JSON products :
```
{
    "id" : VALUE,
    "seller" : VALUE,
    "title" : "VALUE",
    "description" : "VALUE",
    "price" : "VALUE",
    "stock" : VALUE
}
```
<br/>format JSON orders :
```
{
    "note": VALUE,
    "isPaid": VALUE,
    "total": VALUE,
    "discount": VALUE,
    "id": VALUE,
    "buyer": VALUE
}
```
### request method Delete
format path
> http://localhost:8067/{entitas}/{id}
