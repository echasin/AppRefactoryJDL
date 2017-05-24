import { Personpersonmbr } from '../personpersonmbr';
export class Person {
    constructor(
        public id?: number,
        public name?: string,
        public namefirst?: string,
        public parent?: Personpersonmbr,
    ) {
    }
}
