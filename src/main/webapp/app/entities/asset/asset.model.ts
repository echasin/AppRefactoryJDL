import { Assetstatus } from '../assetstatus';
export class Asset {
    constructor(
        public id?: number,
        public name?: string,
        public nameshort?: string,
        public description?: string,
        public details?: string,
        public assetstatus?: Assetstatus,
    ) {
    }
}
