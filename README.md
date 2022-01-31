# SPD Accountancy Application

### Run project

docker-compose up -d --build

### Environment variables

#### AccountancyApp
- SPRING_DATASOURCE_URL: database url and schema
- SPRING_DATASOURCE_USERNAME: database username
- SPRING_DATASOURCE_PASSWORD: database password
- volumes: path to local results directory :/Results

#### TransactinMaker
- USER_SAVE_URI: AccountancyApp save user endpoint url
- ACCOUNT_SAVE_URI: AccountancyApp save account endpoint url
- TRANSACTION_SAVE_URI: AccountancyApp save transaction endpoint url
- POOL_SIZE: number of threads on which the generator task will run, this will be equal also with the number of users
- ACCOUNT_NO: number of accounts per user
- TRANSACTION_NO: number of transactons per user

![alt text](https://github.com/razone24/SPD/blob/master/AccountancyApp.jpeg?raw=true)
