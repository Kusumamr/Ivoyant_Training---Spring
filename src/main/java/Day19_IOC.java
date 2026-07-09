//Tight coupling  - classes directly dependent on each other

class PaymentService1{
    void pay1(){
        System.out.println("Payment Successfully done");
    }
}
class OrderService1{
    private PaymentService1 paymentService1=new PaymentService1();

    void placeOrder1(){
        paymentService1.pay1();
        System.out.println("Order is placed");
    }
}


//used IOC and DI here - promoting loose coupling
class PaymentService2{
    void pay2(){
        System.out.println("Payment successfully done ");
    }
}
class OrderService2{
    private PaymentService2 paymentService2;

    //constructor injection
    OrderService2(PaymentService2 paymentService2){
        this.paymentService2=paymentService2;
    }
    void placeOrder2(){
        paymentService2.pay2();
        System.out.println("The order is placed");
    }


    //setter injection
    public void setPaymentService2(PaymentService2 paymentService2){
        this.paymentService2=paymentService2;
    }

}
public class Day19_IOC {
    public static void main(String[] args) {
        //tight coupling
        OrderService1 orderService1=new OrderService1();
        orderService1.placeOrder1();

        //loose coupling
        PaymentService2 paymentService2=new PaymentService2();
        OrderService2 orderService2=new OrderService2(paymentService2);
        orderService2.placeOrder2();

        //setter injection
        orderService2.setPaymentService2(paymentService2);
    }
}
