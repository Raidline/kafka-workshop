import { For, createSignal } from "solid-js";
import { Cart, CartItem } from "../model/Cart";
import { AppConfig } from "../lib/AppConfig";
import Button from "./primitive/Button";

const ShoppingCart = () => {

  const [shoppingCart, setShoppingCart] = createSignal<CartItem[]>([]);

  const fetchShoppingCart = () => {
    AppConfig.api.getCart().then((c: Cart) => {
      setShoppingCart(c.items);
    });
  }

  const isShoppingCartEmpty = () => {
    return shoppingCart().length === 0;
  }

  const total = () => shoppingCart().reduce((prev, curr) => prev + curr.price, 0);

  const checkout = () => {
    AppConfig.api.clearCart().then();
  }

  fetchShoppingCart();
  setInterval(
    () => fetchShoppingCart(),
    AppConfig.pollingTime
  )

  return (
    <div class="min-w-72 bg-gray-50">
      <h2>
        <i class="fa-solid fa-cart-shopping fa-l"></i>
        <span class="ml-2">Shopping Cart</span>
      </h2>
      <ul class="p-4">
        <For each={shoppingCart()}>
          {(sc) => (
            <li class="flex mt-1">
              <span class="grow-0">{sc.name}</span>
              <span class="grow border-b-2 border-blue-100 border-dotted"></span>
              <span class="grow-0">{sc.price}</span>
              <span class="grow-0">€</span>
            </li>
          )}
        </For>
        <li class="flex font-bold mt-4">
          <span class="grow-0">Total</span>
          <span class="grow border-b-2 border-blue-100 border-dotted"></span>
          <span class="grow-0">{total()}</span>
          <span class="grow-0">€</span>
        </li>
      </ul>
      <div class="mt-6 justify-end flex">
        <Button
            disable={isShoppingCartEmpty()}
            onclick={() => checkout()}
        >Checkout</Button>
      </div>

    </div>
  )
}

export default ShoppingCart;
