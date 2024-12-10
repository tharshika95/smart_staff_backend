Success Response:
{
    "status": "SUCCESS",
    "data": "Data loaded successfully!",
    "error": null
}


Partial Success Response:
{
    "status": "PARTIAL_SUCCESS",
    "data": "Partial data",
    "error": {
        "code": "ERR_PARTIAL",
        "message": "Some data could not be loaded."
    }
}


Failure Response:
{
    "status": "FAILED",
    "data": null,
    "error": {
        "code": "ERR001",
        "message": "Something went wrong."
    }
}


Validation Error Response:
{
    "status": "FAILED",
    "data": null,
    "error": {
        "code": "ERR400",
        "message": "Validation Failed",
        "fieldErrors": [
            {
                "field": "username",
                "message": "Username is required"
            },
            {
                "field": "email",
                "message": "Email format is invalid"
            }
        ]
    }
}
