import {createSignal, onMount, Show} from "solid-js";
import {KafkaStatus} from "../model/KafkaStatus";
import {AppConfig} from "../lib/AppConfig";

const HeaderKafkaStatus = () => {

    const [cartDisable, setCartDisable] = createSignal<boolean>(true);
    const [productStatus, setProductStatus] = createSignal<string>(KafkaStatus.DISCONNETED);
    const [cartStatus, setCartStatus] = createSignal<string>('ok');


    const updateProductStatus = async (val: KafkaStatus) => {
        await AppConfig.api.setProductKafkaStatus(val);
    }

    const updateCartStatus = async (val: string) => {
        const state = parseStringToKafkaStatus(val);
        setCartDisable(true);
        const result: boolean = await AppConfig.api.setCartKafkaStatus(state);
        setCartDisable(false);
        if (result) {
            setCartStatus(state.toString());
        }
    }

    const parseStringToKafkaStatus = (val: string) => {
        switch (val) {
            case 'error':
                return KafkaStatus.NOK;
            case 'ko':
                return KafkaStatus.DISCONNETED;
            default:
                return KafkaStatus.OK;
        }
    }


    onMount(() => {
        AppConfig.api.getProductKafkaStatus().then((status) => {
            setProductStatus(status.toString());
            console.log(productStatus())
        });
        AppConfig.api.getCartKafkaStatus().then((s) => {
            setCartDisable(false);
            setCartStatus(s.toString());
        });
    });

    return (
        <form>
            <div class="flex justify-center align-middle">
                <div class="flex capitalize items-center"> Product Topic Status</div>
                <button class={
                    productStatus() === 'OK' ? 'bg-green-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-green-700 border-green-700 border-2  m-4 px-4 py-2 rounded'
                }
                        onClick={() => updateProductStatus(KafkaStatus.OK)}>Connected
                </button>
                <button class={
                    productStatus() === 'NOK' ? 'bg-yellow-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-yellow-700 border-yellow-700 border-2 m-4 px-4 py-2 rounded'
                }
                        onClick={() => updateProductStatus(KafkaStatus.NOK)}> With Error
                </button>
                <button class={
                    productStatus() === 'DISCONNETED' ? 'bg-red-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-red-700 border-red-700 border-2 m-4 px-4 py-2 rounded'
                }
                        onClick={() => updateProductStatus(KafkaStatus.DISCONNETED)}>Disconnected
                </button>
            </div>
            <br/>
            <Show when={false}>
                <label for="cart">
                    <span>Cart-Promotion topic:</span>
                </label>
                <select
                    id="cart"
                    value={cartStatus()}
                    disabled={cartDisable()}
                    class={cartDisable() ? 'bg-grey-700 cursor-not-allowed' : ''}
                    onchange={e => updateCartStatus(e.target.value)}
                >
                    <option value={'ok'}>Connected</option>
                    <option value={'ko'}>Not Connected</option>
                    <option value={'error'}>Connected With Error</option>
                </select>
            </Show>
        </form>
    )
}

export default HeaderKafkaStatus;

