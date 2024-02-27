import {For, createSignal, mergeProps} from "solid-js";
import {AppConfig} from "../lib/AppConfig";
import {Promotion} from "../model/Promotion";
import Button from "./primitive/Button";


interface PromotionListItemProps {
    promo: Promotion
}

const PromotionListItem = (orifinalProps: PromotionListItemProps) => {

    const props = mergeProps({}, orifinalProps);

    const [value, setValue] = createSignal<number>(props.promo.value);


    const isDisable = () => props.promo.value === value()

    const updatePromo = (promo: Promotion, newValue: number) => {
        AppConfig.api.updatePromo(promo, newValue).then();
    }

    const updateSelector = (num: string) => {
        setValue(Number.parseInt(num));
    }
    const productName = AppConfig.api.products.find(prod => prod.id === props.promo.productId)?.name || ''

    return (
        <tr class="border-b-2 border-blue-100">
            <td class="p-4">{productName}</td>
            <td class="p-4">
                <span>{props.promo.value}</span>
                <span>€</span>
            </td>
            <td class="p-4">
                <select value={value()} onchange={(e) => updateSelector(e.target.value)}>
                    <For each={props.promo.related}>
                        {(num: number) =>
                            <option value={num}>
                                {num}€
                            </option>
                        }
                    </For>
                </select>
            </td>
            <td class="p-4">
                <Button
                    disable={isDisable()}
                    onclick={() => updatePromo(props.promo, value())}
                >Apply</Button>
            </td>
        </tr>
    )
}

export default PromotionListItem;
