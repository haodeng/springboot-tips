Enable ehcahe

    spring.cache.jcache.config=classpath:ehcache.xml

Config 'post' cache in encache.xml

    <cache alias="post">
        <key-type>java.lang.Long</key-type>
        <value-type>demo.hao.model.Post</value-type>
        <expiry>
            <ttl unit="seconds">10000</ttl>
        </expiry>

        <listeners>
            <listener>
                <class>demo.hao.CacheEventLogger</class>
                <event-firing-mode>ASYNCHRONOUS</event-firing-mode>
                <event-ordering-mode>UNORDERED</event-ordering-mode>
                <events-to-fire-on>CREATED</events-to-fire-on>
                <events-to-fire-on>UPDATED</events-to-fire-on>
                <events-to-fire-on>EXPIRED</events-to-fire-on>
                <events-to-fire-on>REMOVED</events-to-fire-on>
                <events-to-fire-on>EVICTED</events-to-fire-on>
            </listener>
        </listeners>

        <resources>
            <heap unit="entries">1000</heap>
            <offheap unit="MB">50</offheap>
        </resources>
    </cache>
    
Demo

    # 1st time. slow
    curl http://localhost:8080/posts/1
    
    in log:
    2021-07-02 21:49:13.843  INFO 55228 --- [nio-8080-exec-1] demo.hao.controller.PostController       : getPostById: 1
    2021-07-02 21:49:13.848  INFO 55228 --- [nio-8080-exec-1] demo.hao.service.PostService             : getPostById, Sleep for 5s, simulate a slow backend
    2021-07-02 21:49:15.861  INFO 55228 --- [e [_default_]-0] demo.hao.CacheEventLogger                : Cache event = CREATED, Key = 1,  Old value = null, New value = Post(id=1, name=test 1)
    
    # fast, from cache
    curl http://localhost:8080/posts/1
    
    
