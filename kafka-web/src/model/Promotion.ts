export class Promotion {
  constructor(
    public readonly id: number,
    public readonly value: number,
    public readonly productId: number,
    public readonly productName: string,
    public readonly related: Array<number>,
  ) {
  }
}
