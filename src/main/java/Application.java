import io.bootique.BaseModule;
import io.bootique.Bootique;

public class Application extends BaseModule {

    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .exec()
                .exit();
    }
}
