import {Cart, CartItem} from "../../../model/Cart";
import {KafkaStatus} from "../../../model/KafkaStatus";
import {Product} from "../../../model/Product";
import {Promotion} from "../../../model/Promotion";
import {Api} from "../Api";
import axios from 'axios';

export class RealApi extends Api {
    get productUrl() {
        return `${import.meta.env.VITE_PRODUCT_URL}/product`
    }

    get promoUrl() {
        return `${import.meta.env.VITE_PROMO_URL}/promo`
    }

    get shoppingCartUrl() {
        return import.meta.env.VITE_SHOPPING_CART_URL
    }

    products: Array<Product> = [];
    promos: Array<Promotion> = [];
    axiosInstance;

    constructor(
        private readonly isFinalFase: boolean
    ) {
        super();
        this.axiosInstance = axios.create()
    }


    getProductKafkaStatus(): Promise<KafkaStatus> {
        return this.axiosInstance.get(`${this.productUrl}/kafka/status`).then(res => res.data)
    }

    getCartKafkaStatus(): Promise<KafkaStatus> {
        return this.axiosInstance.get(`${this.shoppingCartUrl}/kafka/status`).then(res => res.data)
    }

    setProductKafkaStatus(status: KafkaStatus): Promise<boolean> {
        return this.axiosInstance.post(`${this.productUrl}/kafka/${status}`)
    }

    setCartKafkaStatus(status: KafkaStatus): Promise<boolean> {
        return this.axiosInstance.post(`${this.shoppingCartUrl}/kafka/${status}`)
    }

    getProducts(): Promise<Product[]> {
        return this.axiosInstance.get(`${this.productUrl}/promo`)
            .then(res => res.data)
            .then(
                (res: Array<any>) => res.map(item => new Product(item.prodId, item.prodName, item.originalValue, item.finalValue, item.related))
            ).then(res => this.products = res)
    }

    getCart(): Promise<Cart> {
        return this.axiosInstance.get(`${this.productUrl}/cart`)
            .then(res => res.data)
            .then(
                (res: Array<any>) => new Cart(res.map(item => new CartItem(item.prodName, item.value))));
    }

    getPromos(): Promise<Promotion[]> {
        return this.axiosInstance.get(`${this.promoUrl}`)
            .then(res => res.data)
            .then((res: Array<any>) => res.map(item => new Promotion(item.id, item.value, item.productId,
                '', item.options))
            ).then(res => this.promos = res)


    }

    updatePromo(promo: Promotion, value: number): Promise<Promotion> {
        return this.axiosInstance.put(`${this.promoUrl}/${promo.id}`,
            {
                option: value
            },
            {
                headers:
                    {
                        'Content-Type': 'application/json'
                    }
            }).then(res => res.data)

    }

    addProductToCart(product: Product): Promise<boolean> {
        return this.axiosInstance.post(`${this.productUrl}/add?id=${product.id}`)
            .then(res => res.data)

    }

    clearCart(): Promise<boolean> {
        return this.axiosInstance.post(`${this.productUrl}/clear`)
    }


}