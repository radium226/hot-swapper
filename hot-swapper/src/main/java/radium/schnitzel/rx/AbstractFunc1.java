package radium.schnitzel.rx;

import rx.functions.Func1;

public abstract class AbstractFunc1<T1, R> implements Func1<T1, R> {

    public AbstractFunc1() {
        super();
    }

    /*public <B1> Func1<B1, T1> compose(Func1<B1, T1> func1) {
     return new AbstractFunc1<B1, T1>() {

     @Override
     public T1 call(B1 term1) {
     return AbstractFunc1.this.call(func1.call(term1));
     }
			
     };
     }*/
    @Override
    public abstract R call(T1 term1);

}
