export class Product {
  constructor(
    public readonly id: number,
    public readonly name: string,
    public readonly originalPrice: number,
    public readonly finalPrice: number,
    public readonly related: Array<string>,
  ) {
  }
}
