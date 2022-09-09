# GitHub Proxy

GitHub Proxy is the project to browse GitHub users repositories.
In this pretty simple API you can browse chosen user's repositories and it's branches.
Main endpoint you can use is

## API Reference

#### Get all user's repositories by login (username)

```http
  GET /api/github/{login}/repositories
```

| Parameter | Type     | Description                              |
| :-------- | :------- | :--------------------------------------: |
| `login`   | `String` | **Required**. Chosen GitHub user's login |

Available headers:

| Parameter  | Type     | Value            |
| :--------- | :------- | :--------------: |
| `Accept`   | `String` | application/json |


Example response:
```json
[
    {
        "name": "Hello World",
        "owner": "JohnSmith123",
        "branches": [
            {
                "name": "master",
                "sha": "c8e8f1b026c4840fed7376d33786f2c721375ae6"
            },
            {
                "name": "feature/FEATURE-01",
                "sha": "ec4ab42080e536cdfd628fc858b6d53992a90ef0"
            }
        ]
    },
    {
        "name": "Test",
        "owner": "JohnSmith123",
        "branches": [
            {
                "name": "master",
                "sha": "60c3b002b742176770fa742ceab323e731966b9a"
            }
        ]
    }
]
```
Example response when user not found:
```json
{
    "statusCode": 404,
    "message": "Not Found"
}
```

Example response when header `Accept` is invalid:
```json
{
    "statusCode": 406,
    "message": "Invalid media type \"application/xml\": Invalid media type header"
}
```

## Installation

In the `\github-proxy>` folder, run these commands:
1. `mvn clean install`
2. `docker build -t github-proxy .`
3. `docker run --name github-proxy-app -p 8080:8080 github-proxy:latest`