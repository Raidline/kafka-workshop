import { Cart, CartItem } from "../../../model/Cart";
import { KafkaStatus } from "../../../model/KafkaStatus";
import { Product } from "../../../model/Product";
import { Promotion } from "../../../model/Promotion";
import { Api } from "../Api";

export class FakeApi extends Api {
  cart: Cart = new Cart([]);
  products: Array<Product> = [
    new Product(1, 'aaa', 100, 100, []),
    new Product(2, 'bbb', 200, 200, []),
    new Product(3, 'ccc', 300, 300, []),
  ];
  promos: Array<Promotion> = [
    new Promotion(1, 100, 1, 'aaa', [100, 90, 80]),
    new Promotion(2, 200, 2, 'bbb', [200, 190, 180]),
    new Promotion(3, 300, 3, 'ccc', [300, 290, 280])
  ];
  promotionStatus: KafkaStatus = KafkaStatus.OK;
  cartStatus: KafkaStatus = KafkaStatus.OK;

  getProducts(): Promise<Product[]> {
    return Promise.resolve([...this.products]);
  }

  getCart(): Promise<Cart> {
    return Promise.resolve(new Cart([...this.cart.items]));
  }

  getPromos(): Promise<Promotion[]> {
    return Promise.resolve([...this.promos]);
  }

  updatePromo(promo: Promotion, value: number): Promise<Promotion> {
    const newPromo = new Promotion(
      promo.id,
      value,
      promo.productId,
      promo.productName,
      promo.related
    )

    let idx = this.promos.findIndex(p => p.id === promo.id);
    this.promos[idx] = newPromo;

    idx = this.products.findIndex(p => p.id === promo.productId);
    this.products[idx] = new Product(
      this.products[idx].id,
      this.products[idx].name,
      this.products[idx].originalPrice,
      value,
      this.products[idx].related,
    )


    return Promise.resolve(newPromo);
  }

  addProductToCart(product: Product): Promise<boolean> {
    this.cart.items.push(new CartItem(product.name, product.finalPrice));
    return Promise.resolve(true);
  }

  clearCart(): Promise<boolean> {
    while (this.cart.items.length > 0) {
      this.cart.items.pop()
    }

    return Promise.resolve(true);
  }

  getProductKafkaStatus(): Promise<KafkaStatus> {
    return Promise.resolve(this.promotionStatus);
  }

  getCartKafkaStatus(): Promise<KafkaStatus> {
    return Promise.resolve(this.cartStatus);
  }

  setProductKafkaStatus(status: KafkaStatus): Promise<boolean> {
    this.promotionStatus = status;
    return new Promise((resolve) => {
      setTimeout(
        () => resolve(true),
        2000
      );
    })
  }
  setCartKafkaStatus(status: KafkaStatus): Promise<boolean> {
    this.cartStatus = status;
    return new Promise((resolve) => {
      setTimeout(
        () => resolve(true),
        2000
      );
    })
  }

}
