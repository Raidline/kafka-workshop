import { For, createSignal } from "solid-js";
import { Product } from '../model/Product';
import { AppConfig } from "../lib/AppConfig";
import Button from "./primitive/Button";

const ProductSection = () => {

  const [products, setProducts] = createSignal<Product[]>([]);
  const fetchProduct = () => AppConfig.api.getProducts().then((p: Array<Product>) => setProducts(p));

  setInterval(
    () => {
      fetchProduct();
    },
    AppConfig.pollingTime
  );


  const addToCard = async (p: Product) => {
    await AppConfig.api.addProductToCart(p)
  }

  return <section id="section_product">
    <table>
      <thead>
      <tr class="border-b-2 border-blue-100">
        <th class="p-4">Name</th>
        <th class="p-4">Original Value</th>
        <th class="p-4">Final Value</th>
        <th class="p-4">Add to Cart</th>
      </tr>
      </thead>
      <tbody>
      <For each={products()}>
          {(p: Product) => (
            <tr class="border-b-2 border-blue-100">
              <td class="p-4">{p.name}</td>
              <td class="p-4">{p.originalPrice}€</td>
              <td class="p-4">{p.finalPrice}€</td>
              <td  class="p-4">
                <Button onclick={() => addToCard(p)}>+ add</Button>
              </td>
            </tr>
          )}
        </For>
      </tbody>
    </table>
  </section>
}

export default ProductSection;
