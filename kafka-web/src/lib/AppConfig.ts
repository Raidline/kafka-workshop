import { FakeApi } from "./api/implementations/FakeApi";
import { RealApi } from "./api/implementations/RealApi";

const isFinal = import.meta.env.VITE_FINAL_FASE === 'true';

export const AppConfig = {
  productUrl: import.meta.env.VITE_PRODUCT_URL ? import.meta.env.VITE_PRODUCT_URL : "http://localhost:8080",
  isFinalFase: isFinal,
  api: import.meta.env.VITE_API_FAKE === "true" ? new FakeApi() : new RealApi(isFinal),
  pollingTime: parsePolling(import.meta.env.VITE_POLLING_TIME)
};

function parsePolling(env: string | undefined): number {
  if (env && !Number.isNaN(env)) {
    return Number.parseInt(env);
  }
  return 4000;
}

