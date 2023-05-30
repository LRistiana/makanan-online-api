# makanan-online-api
pembuat @LRistiana <br/>
Port : 8067

## Spesifikasi Program
API ini dapat menghandle empat request method yaitu
* GET
* POST
* PUT
* DELETE


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
### request method Put
## request method Delete
