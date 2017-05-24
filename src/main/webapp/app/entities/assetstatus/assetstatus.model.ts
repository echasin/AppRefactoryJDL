import { Asset } from '../asset';
export class Assetstatus {
    constructor(
        public id?: number,
        public name?: string,
        public nameshort?: string,
        public description?: string,
        public asset?: Asset,
    ) {
    }
}
