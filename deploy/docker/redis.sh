sudo docker run \
    --restart=always \
    --log-opt max-size=100m \
    --log-opt max-file=2 \
    -p 6379:6379 \
    --name redis \
    -v /opt/redis/conf/redis.conf:/etc/redis/redis.conf \
    -v /opt/redis/data:/data \
    -d redis \
    redis-server /etc/redis/redis.conf \
    --appendonly yes \
    --requirepass 5KNXvtUSWsmsXKw4ySWb