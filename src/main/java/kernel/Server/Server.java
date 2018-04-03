package kernel.Server;

public abstract class Server {
        public abstract String getServerName();

        public Server() throws Exception{
            init();
        }

        protected abstract void init() throws Exception;

        public abstract void start() throws Exception;

}
