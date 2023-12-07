# Filebox

## Getting started

1. Download the project
2. Open the application.properties file and replace the placeholder CONNECTION_STRING with your valid database details.
3. Run FileboxApplication.java

## Project Overview

Filebox is a graded project focused on building a Spring Boot API for efficient file management. Key functionalities include:

### User Management:

- Users can register and log in.
- JWT tokens are implemented for enhanced security.

### File Operations:

- Create folders as a logged-in user.
- Upload and download files securely.
- Delete files when needed.

This project emphasizes security measures through JWT tokens, ensuring users can only access and modify their own files.

## Features

#### 1. User Registration

```
- Method: POST
- Endpoint: /register
- Body (JSON): 
{
    "username":"EXAMPLE_USER",
    "password": "EXAMPLE_PASSWORD"
}

```

#### 2. User Login

```
- Method: POST
- Endpoint: /login
- Body (JSON): 
{
    "username":"EXAMPLE_USER",
    "password": "EXAMPLE_PASSWORD"
}
```

#### 3. Add a Folder

```
- Method: POST
- Endpoint: /add-folder
- Header: Key: Authorization, Value: JWT token
- Request Parameters:
  - Key: folderName, Value: (string): The name of the folder to be added.
  - Key: username, Value: (string): The username of the user to whom the folder will belong.
```

#### 3. Upload File

```
- Method: POST
- Endpoint: /{username}/upload-file
- Header: Key: Authorization, Value: JWT token
- Request Parameters:
  - Key: folderName, Value: (string): The name of the folder where the file should be uploaded.
- Body (form-data): 
  - Key: file, Value: (Select file)
```

#### 3. Delete File

```
- Method: DELETE
- Endpoint: /{username}/delete
- Header: Key: Authorization, Value: JWT token
- Request Parameters:
  - Key: fileId, Value: (int): The id of the file that should be deleted.
```

#### 3. Download File

```
- Method: GET
- Endpoint: /{username}/download
- Header: Key: Authorization, Value: JWT token
- Request Parameters:
  - Key: fileId, Value: (int): The id of the file that should be deleted.
```