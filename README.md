# makanan-online-api
pembuat @LRistiana
Port : 8067

## Spesifikasi Program
API ini dapat menghandle empat request method yaitu
* GET
* POST
* PUT
* DELETE
<br/>
masing-masing  request method dapat menghandle data users, orders, dan products di dalam database

## Menggunakan Program
### Request Method GET
request method get dapat digunakan untuk menghandle table orders, users, dan products
#### users handle
get all user
>http://localhost:8067/users
akan menghasilkan JSON berupa
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
