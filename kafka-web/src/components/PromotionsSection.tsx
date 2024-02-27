import { For, createSignal } from "solid-js";
import { Promotion } from "../model/Promotion";
import { AppConfig } from "../lib/AppConfig";
import PromotionListItem from "./PromotionListItem";

const PromotionsSection = () => {

  const [promotions, setPromotions] = createSignal<Promotion[]>([]);
  const fetchPromotion = () => {
    AppConfig.api.getPromos().then((promos: Promotion[]) => {
      setPromotions(promos);
    });
  }

  fetchPromotion();
  setInterval(
    () => fetchPromotion(),
    AppConfig.pollingTime
  );

  return (
    <section id="promotion-section">
      <table>
        <thead>
        <tr class="border-b-2 border-blue-100">
          <th class="p-4">Name</th>
          <th class="p-4">Value</th>
          <th class="p-4">Change value</th>
          <th class="p-4">Action</th>
        </tr>
        </thead>
        <tbody>
        <For each={promotions()}>
            {(promo: Promotion) => (
              <PromotionListItem promo={promo} />
            )}
          </For>
        </tbody>
      </table>
    </section>
  )
}

export default PromotionsSection;
