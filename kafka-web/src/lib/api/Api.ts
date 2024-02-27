import {Cart} from "../../model/Cart";
import {KafkaStatus} from "../../model/KafkaStatus";
import {Product} from "../../model/Product";
import {Promotion} from "../../model/Promotion";

export abstract class Api {
    public abstract products: Array<Product>
    public abstract promos: Array<Promotion>

    abstract getProducts(): Promise<Array<Product>>;

    abstract getCart(): Promise<Cart>;

    abstract getPromos(): Promise<Array<Promotion>>;

    abstract updatePromo(promo: Promotion, value: number): Promise<Promotion>;

    abstract addProductToCart(product: Product): Promise<boolean>;

    abstract clearCart(): Promise<boolean>;

    abstract getProductKafkaStatus(): Promise<KafkaStatus>;

    abstract getCartKafkaStatus(): Promise<KafkaStatus>;

    abstract setProductKafkaStatus(status: KafkaStatus): Promise<boolean>;

    abstract setCartKafkaStatus(status: KafkaStatus): Promise<boolean>;

}

