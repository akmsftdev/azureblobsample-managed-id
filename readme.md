
# Azure CLI or PowerShell commands to:
# 1. Create an App Registration in Azure AD.
# 2. Generate a client secret for the App Registration.
# 3. Assign the "Blob Storage Account Administrator" role to the App's service principal.
# 4. Output the client ID and secret.

# Replace <resource-group-name> and <storage-account-name> with your actual resource names.

# Create App Registration
$app = az ad app create --display-name "YourAppName"

# Get App Registration details
$appId = $app.appId
$secret = az ad app credential reset --id $appId --append

# Assign role to the service principal
$spId = az ad sp create --id $appId
az role assignment create --assignee $spId --role "Storage Blob Data Contributor" --scope /subscriptions/<your-subscription-id>/resourceGroups/<resource-group-name>/providers/Microsoft.Storage/storageAccounts/<storage-account-name>

# Output client ID and secret
echo "Client ID: $appId"
echo "Secret: $secret"
