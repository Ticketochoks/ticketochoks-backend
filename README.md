# Ticketochoks

A platform for buying and managing event tickets.

## Prerequisites
Before running this project, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
    - Verify installation: `java -version`
    - Ensure JAVA_HOME is set in your environment variables
- **PostgreSQL** database server version 16 or higher
    - Verify installation: `psql --version`
    - Ensure PostgreSQL service is running


## Build and run
### 1. Clone the repository

```bash
git clone <repository-url>
cd ticketochoks
```

### 2. Connect to PostgreSQL and create the required database

### 3. Environment Configuration

#### For Windows (PowerShell)

1. Open and edit `start-dev.ps1`:

2. Configure the following variables:
   ```powershell
   $env:DB_URL
   $env:DB_USERNAME
   $env:DB_PASSWORD
   ```

3. Run the application:
   ```powershell
   .\start-dev.ps1
   ```

#### For Linux/macOS (Bash)

1. Make the script executable:
   ```bash
   chmod +x start-dev.sh
   ```

2. Update the environment variables:
   ```bash
   export DB_URL
   export DB_USERNAME
   export DB_PASSWORD
   ```

3. Run the application:
   ```bash
   ./start-dev.sh
   ```