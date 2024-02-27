import {createSignal, onMount, Show} from "solid-js";
import {KafkaStatus} from "../model/KafkaStatus";
import {AppConfig} from "../lib/AppConfig";

const HeaderKafkaStatus = () => {
    const [productStatus, setProductStatus] = createSignal<string>(KafkaStatus.DISCONNETED);
    const [cartStatus, setCartStatus] = createSignal<string>('ok');


    const updateProductStatus = async (val: KafkaStatus) => {
        await AppConfig.api.setProductKafkaStatus(val);
    }

    const updateCartStatus = async (val: KafkaStatus) => {
        await AppConfig.api.setProductKafkaStatus(val);
    }


    onMount(() => {
        AppConfig.api.getProductKafkaStatus().then((status) => {
            setProductStatus(status.toString());
        });
        AppConfig.api.getCartKafkaStatus().then((s) => {
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
            <Show when={AppConfig.isFinalFase}>

                <div class="flex justify-center align-middle">
                    <div class="flex capitalize items-center"> Shopping Cart Topic Status</div>
                    <button class={
                        cartStatus() === 'OK' ? 'bg-green-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-green-700 border-green-700 border-2  m-4 px-4 py-2 rounded'
                    }
                            onClick={() => updateCartStatus(KafkaStatus.OK)}>Connected
                    </button>
                    <button class={
                        cartStatus() === 'NOK' ? 'bg-yellow-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-yellow-700 border-yellow-700 border-2 m-4 px-4 py-2 rounded'
                    }
                            onClick={() => updateCartStatus(KafkaStatus.NOK)}> With Error
                    </button>
                    <button class={
                        cartStatus() === 'DISCONNETED' ? 'bg-red-700 text-white m-4 px-4 py-2 rounded' : 'bg-white text-red-700 border-red-700 border-2 m-4 px-4 py-2 rounded'
                    }
                            onClick={() => updateCartStatus(KafkaStatus.DISCONNETED)}>Disconnected
                    </button>
                </div>

            </Show>
        </form>
    )
}

export default HeaderKafkaStatus;

