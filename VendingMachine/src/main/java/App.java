import com.sg.vendingmachine.controller.VendingMachineController;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {


        AnnotationConfigApplicationContext appContext =new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.vendingmachine");
        appContext.refresh();


        VendingMachineController controller =appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();

    }
}
