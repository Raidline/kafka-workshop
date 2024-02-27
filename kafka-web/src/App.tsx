import type { Component } from 'solid-js';
import ProductSection from "./components/ProductSection";
import Header from './components/Header';
import ShoppingCart from './components/ShoppingCart';
import PromotionsSection from './components/PromotionsSection';
import HeaderKafkaStatus from "./components/HeaderKafkaStatus";

const App: Component = () => {
  return (
    <>
      <Header />
        <HeaderKafkaStatus />

      <section
        id="content"
        class="flex justify-around mt-6"
      >
        <ProductSection />
        <PromotionsSection />
        <ShoppingCart />
      </section>
    </>
  );
};

export default App;

