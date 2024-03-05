docker run -d -p 6379:6379 --name my-redis-container redis

docker exec -it my-redis-container redis-cli
