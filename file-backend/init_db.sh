#!/bin/bash

set -e

echo "Aguardando o MySQL iniciar..."
while ! nc -z localhost 3306; do
  sleep 1
done

echo "Criando banco de dados 'file' se não existir..."
mysql -u root -p1234 -e "CREATE DATABASE IF NOT EXISTS file;"

echo "Concedendo privilégios ao usuário 'root'..."
mysql -u root -p1234 -e "GRANT ALL PRIVILEGES ON file.* TO 'root'@'%' IDENTIFIED BY '1234' WITH GRANT OPTION;"
mysql -u root -p1234 -e "FLUSH PRIVILEGES;"

echo "Banco de dados inicializado com sucesso!"
