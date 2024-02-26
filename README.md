# Workshop

How to run the project:

```bash
sh init.sh
```

This will run the script that will start the dependencies(kafka, db).

In intellij you can run the `ProductApplication` class and `PromoApplication class`.

This will start the product and promo services.

## Stage 1

### See that everything is working

You should be able to the portal and put items on the cart.

You should also be able to update the promotions and see the effects on the products and cart.


### Disconnect error

In this step you will force the product service to unsubscribe from kafka service.

This will have the following effects:
- The product service will not be able to update the products in the cart.
- The product service will not be able to update the products in the portal.

You should be able to see the constant unsubscribe in the logs.

Now if you choose the connected option again, you should see the product service reconnecting to the kafka service.

And the products in the cart and portal should be updated again, with the last event sent.


### Forced error

In this step you will force the promo service to throw an error.

This will have the following effects:
- The product service will not be able to update the products in the portal.
- The product service will not be able to update the products in the cart.

You should be able to see the error in the logs.

In this case, if you send the update again the error will keep happening.

Your task for this step is to implement an error handling mechanism in the product service, so that it can recover from the error and keep updating the products in the portal and cart.

There is a couple of things you should be aware of:
- You should create a new topic for the error.
- You should send the error to the new topic.
- The promo update should be rollbacked in the promo service.
  - This mean that the old value should be restored.

Steps:
- Create a new topic for the error.
  - The topic can be called whatever you want.
  - **The producer is the product service.**
  - **The consumer is the promo service.**
- Send the error to the new topic.
  - Use the already existent code in the promo service as an inspiration (for the producer).
  - Use the already existent code in the product service as an inspiration (for the consumer).

After you implement the error handling mechanism, if choose the connected option again you should be able to see the product service recovering from the error and updating the products in the portal and cart again.

## Stage 2
