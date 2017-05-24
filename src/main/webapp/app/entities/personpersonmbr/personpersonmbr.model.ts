import { Person } from '../person';
export class Personpersonmbr {
    constructor(
        public id?: number,
        public comment?: string,
        public parentmbr?: Person,
    ) {
    }
}
