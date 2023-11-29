# Filebox

## Getting started

1. Download the project
2. Configure Environment Variables:
- Locate the file named .env.example in the resource folder.
- Rename it to .env or create a new file named .env in the resource folder.
- Open the .env file and provide the necessary connection details.
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