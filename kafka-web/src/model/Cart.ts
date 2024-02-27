export class Cart {
  constructor(
    public readonly items: Array<CartItem>
  ) {
  }
}

export class CartItem {
  constructor(
    public readonly name: string,
    public readonly price: number
  ) {
  }
}
