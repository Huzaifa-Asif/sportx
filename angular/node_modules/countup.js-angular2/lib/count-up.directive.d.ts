import { ElementRef, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
export declare class CountUpDirective implements OnChanges {
    private el;
    countUp: any;
    options: any;
    startVal: number;
    endVal: number;
    duration: number;
    decimals: number;
    reanimateOnClick: boolean;
    onClick(): void;
    complete: EventEmitter<void>;
    constructor(el: ElementRef);
    ngOnChanges(changes: SimpleChanges): void;
    private createCountUp;
    private animate;
}
