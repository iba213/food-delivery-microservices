#!/usr/bin/env bash
set -e

SPRING_BOOT_VERSION="4.0.2"
JAVA_VERSION="17"
PROJECT_VERSION="0.0.1-SNAPSHOT"
GROUP_ID="com.champsoft"

generate_service() {
  SERVICE_NAME="$1"
  PACKAGE_NAME="$2"
  DEPENDENCIES="$3"

  if [ -d "$SERVICE_NAME" ]; then
    echo "Skipping $SERVICE_NAME (already exists)"
    return
  fi

  echo "Generating $SERVICE_NAME ..."
  spring init \
    --boot-version="$SPRING_BOOT_VERSION" \
    --build=gradle \
    --type=gradle-project \
    --java-version="$JAVA_VERSION" \
    --packaging=jar \
    --name="$SERVICE_NAME" \
    --package-name="$PACKAGE_NAME" \
    --groupId="$GROUP_ID" \
    --dependencies="$DEPENDENCIES" \
    --version="$PROJECT_VERSION" \
    "$SERVICE_NAME"
}

# API Gateway
generate_service "api-gateway" "com.champsoft.fooddelivery.gateway" "web,validation"

# Orchestrator (orders = central logic)
generate_service "orders-orchestrator" "com.champsoft.fooddelivery.orders" "web,validation,hateoas"

# Supporting services
generate_service "customers-service" "com.champsoft.fooddelivery.customers" "web,validation"
generate_service "drivers-service" "com.champsoft.fooddelivery.drivers" "web,validation"
generate_service "restaurants-service" "com.champsoft.fooddelivery.restaurants" "web,validation"

echo
echo "All microservice skeletons generated successfully."