(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? factory(exports, require('@angular/core'), require('countup.js')) :
    typeof define === 'function' && define.amd ? define('countup.js-angular2', ['exports', '@angular/core', 'countup.js'], factory) :
    (factory((global.countup = global.countup || {}, global.countup['js-angular2'] = {}),global.ng.core,global.CountUp));
}(this, (function (exports,core,CountUp) { 'use strict';

    /**
     * @fileoverview added by tsickle
     * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
     */
    var CountUpDirective = /** @class */ (function () {
        function CountUpDirective(el) {
            this.el = el;
            // on-complete event emitter
            this.complete = new core.EventEmitter();
        }
        // Re-animate if preference is set.
        // Re-animate if preference is set.
        /**
         * @return {?}
         */
        CountUpDirective.prototype.onClick =
            // Re-animate if preference is set.
            /**
             * @return {?}
             */
            function () {
                if (this.reanimateOnClick) {
                    this.animate();
                }
            };
        /**
         * @param {?} changes
         * @return {?}
         */
        CountUpDirective.prototype.ngOnChanges = /**
         * @param {?} changes
         * @return {?}
         */
            function (changes) {
                if (changes.endVal && changes.endVal.currentValue) {
                    this.countUp = this.createCountUp();
                    this.animate();
                }
            };
        /**
         * @return {?}
         */
        CountUpDirective.prototype.createCountUp = /**
         * @return {?}
         */
            function () {
                /** @type {?} */
                var start = this.startVal || 0;
                /** @type {?} */
                var duration = this.duration || 2;
                /** @type {?} */
                var decimals = this.decimals || 0;
                if (!this.duration) {
                    this.duration = duration;
                }
                // construct countUp
                /** @type {?} */
                var countUp = new CountUp(this.el.nativeElement, start, this.endVal, decimals, duration, this.options);
                /** @type {?} */
                var diff = Math.abs(this.endVal - start);
                // make easing smoother for large numbers
                if (diff > 999) {
                    /** @type {?} */
                    var up = (this.endVal > start) ? -1 : 1;
                    countUp = new CountUp(this.el.nativeElement, start, this.endVal + (up * 100), decimals, duration / 2, this.options);
                }
                return countUp;
            };
        /**
         * @return {?}
         */
        CountUpDirective.prototype.animate = /**
         * @return {?}
         */
            function () {
                var _this = this;
                this.countUp.reset();
                if (this.endVal > 999) {
                    this.countUp.start(function () { return _this.countUp.update(_this.endVal); });
                    setTimeout(function () {
                        _this.complete.emit();
                    }, this.duration * 1000);
                }
                else {
                    this.countUp.start(function () {
                        _this.complete.emit();
                    });
                }
            };
        CountUpDirective.decorators = [
            { type: core.Directive, args: [{
                        selector: '[countUp]'
                    },] }
        ];
        CountUpDirective.ctorParameters = function () {
            return [
                { type: core.ElementRef }
            ];
        };
        CountUpDirective.propDecorators = {
            options: [{ type: core.Input, args: ['countUp',] }],
            startVal: [{ type: core.Input }],
            endVal: [{ type: core.Input }],
            duration: [{ type: core.Input }],
            decimals: [{ type: core.Input }],
            reanimateOnClick: [{ type: core.Input }],
            onClick: [{ type: core.HostListener, args: ['click',] }],
            complete: [{ type: core.Output }]
        };
        return CountUpDirective;
    }());

    /**
     * @fileoverview added by tsickle
     * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
     */
    var CountUpModule = /** @class */ (function () {
        function CountUpModule() {
        }
        CountUpModule.decorators = [
            { type: core.NgModule, args: [{
                        imports: [],
                        declarations: [CountUpDirective],
                        exports: [CountUpDirective]
                    },] }
        ];
        return CountUpModule;
    }());

    /**
     * @fileoverview added by tsickle
     * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
     */

    /**
     * @fileoverview added by tsickle
     * @suppress {checkTypes,extraRequire,missingReturn,uselessCode} checked by tsc
     */

    exports.CountUpModule = CountUpModule;
    exports.ɵa = CountUpDirective;

    Object.defineProperty(exports, '__esModule', { value: true });

})));

//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY291bnR1cC5qcy1hbmd1bGFyMi51bWQuanMubWFwIiwic291cmNlcyI6WyJuZzovL2NvdW50dXAuanMtYW5ndWxhcjIvbGliL2NvdW50LXVwLmRpcmVjdGl2ZS50cyIsIm5nOi8vY291bnR1cC5qcy1hbmd1bGFyMi9saWIvY291bnQtdXAubW9kdWxlLnRzIl0sInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7XG4gIERpcmVjdGl2ZSxcbiAgRWxlbWVudFJlZixcbiAgSW5wdXQsXG4gIE91dHB1dCxcbiAgSG9zdExpc3RlbmVyLFxuICBFdmVudEVtaXR0ZXIsXG4gIE9uQ2hhbmdlcyxcbiAgU2ltcGxlQ2hhbmdlc1xufSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCAqIGFzIENvdW50VXAgZnJvbSAnY291bnR1cC5qcyc7XG5cbkBEaXJlY3RpdmUoe1xuICBzZWxlY3RvcjogJ1tjb3VudFVwXSdcbn0pXG5leHBvcnQgY2xhc3MgQ291bnRVcERpcmVjdGl2ZSBpbXBsZW1lbnRzIE9uQ2hhbmdlcyB7XG5cbiAgY291bnRVcDogYW55O1xuICAvLyBPcHRpb25hbCBleHRyYSBjb25maWd1cmF0aW9uLCBzdWNoIGFzIGVhc2luZy5cbiAgQElucHV0KCdjb3VudFVwJykgb3B0aW9uczogYW55O1xuXG4gIC8vIE9wdGlvbmFsIHN0YXJ0IHZhbHVlIGZvciB0aGUgY291bnQuIERlZmF1bHRzIHRvIHplcm8uXG4gIEBJbnB1dCgpIHN0YXJ0VmFsOiBudW1iZXI7XG5cbiAgLy8gdGhlIG51bWJlciB0byBjb3VudCB0b1xuICBASW5wdXQoKSBlbmRWYWw6IG51bWJlcjtcblxuICAvLyBPcHRpb25hbCBkdXJhdGlvbiBvZiB0aGUgYW5pbWF0aW9uIGluIHNlY29uZHMuIERlZmF1bHQgaXMgMi5cbiAgQElucHV0KCkgZHVyYXRpb246IG51bWJlcjtcblxuICAvLyBPcHRpb25hbCBudW1iZXIgb2YgZGVjaW1hbCBwbGFjZXMuIERlZmF1bHQgaXMgMi5cbiAgQElucHV0KCkgZGVjaW1hbHM6IG51bWJlcjtcbiAgXG4gIC8vIE9wdGlvbmFsIGZsYWcgZm9yIHNwZWNpZnlpbmcgd2hldGhlciB0aGUgZWxlbWVudCBzaG91bGQgcmUtYW5pbWF0ZSB3aGVuIGNsaWNrZWQuXG4gIC8vIERlZmF1bHQgaXMgdHJ1ZS5cbiAgQElucHV0KCkgcmVhbmltYXRlT25DbGljazogYm9vbGVhbjtcblxuICAvLyBSZS1hbmltYXRlIGlmIHByZWZlcmVuY2UgaXMgc2V0LlxuICBASG9zdExpc3RlbmVyKCdjbGljaycpXG4gIG9uQ2xpY2soKSB7XG4gICAgaWYgKHRoaXMucmVhbmltYXRlT25DbGljaykge1xuICAgICAgdGhpcy5hbmltYXRlKCk7XG4gICAgfVxuICB9XG5cbiAgLy8gb24tY29tcGxldGUgZXZlbnQgZW1pdHRlclxuICBAT3V0cHV0KCkgY29tcGxldGUgPSBuZXcgRXZlbnRFbWl0dGVyPHZvaWQ+KCk7XG5cbiAgY29uc3RydWN0b3IocHJpdmF0ZSBlbDogRWxlbWVudFJlZikge31cblxuICBuZ09uQ2hhbmdlcyhjaGFuZ2VzOiBTaW1wbGVDaGFuZ2VzKSB7XG4gICAgaWYgKGNoYW5nZXMuZW5kVmFsICYmIGNoYW5nZXMuZW5kVmFsLmN1cnJlbnRWYWx1ZSkge1xuICAgICAgdGhpcy5jb3VudFVwID0gdGhpcy5jcmVhdGVDb3VudFVwKCk7XG4gICAgICB0aGlzLmFuaW1hdGUoKTtcbiAgICB9XG4gIH1cblxuICBwcml2YXRlIGNyZWF0ZUNvdW50VXAoKTogYW55IHtcbiAgICBjb25zdCBzdGFydCA9IHRoaXMuc3RhcnRWYWwgfHwgMDtcbiAgICBjb25zdCBkdXJhdGlvbiA9IHRoaXMuZHVyYXRpb24gfHwgMjtcbiAgICBjb25zdCBkZWNpbWFscyA9IHRoaXMuZGVjaW1hbHMgfHwgMDtcblxuICAgIGlmICghdGhpcy5kdXJhdGlvbikge1xuICAgICAgdGhpcy5kdXJhdGlvbiA9IGR1cmF0aW9uO1xuICAgIH1cblxuICAgIC8vIGNvbnN0cnVjdCBjb3VudFVwXG4gICAgbGV0IGNvdW50VXAgPSBuZXcgQ291bnRVcCh0aGlzLmVsLm5hdGl2ZUVsZW1lbnQsIHN0YXJ0LCB0aGlzLmVuZFZhbCwgZGVjaW1hbHMsIGR1cmF0aW9uLCB0aGlzLm9wdGlvbnMpO1xuICAgIGNvbnN0IGRpZmYgPSBNYXRoLmFicyh0aGlzLmVuZFZhbCAtIHN0YXJ0KTtcbiAgICAvLyBtYWtlIGVhc2luZyBzbW9vdGhlciBmb3IgbGFyZ2UgbnVtYmVyc1xuICAgIGlmIChkaWZmID4gOTk5KSB7XG4gICAgICBjb25zdCB1cCA9ICh0aGlzLmVuZFZhbCA+IHN0YXJ0KSA/IC0xIDogMTtcbiAgICAgIGNvdW50VXAgPSBuZXcgQ291bnRVcCh0aGlzLmVsLm5hdGl2ZUVsZW1lbnQsIHN0YXJ0LCB0aGlzLmVuZFZhbCArICh1cCAqIDEwMCksIGRlY2ltYWxzLCBkdXJhdGlvbiAvIDIsIHRoaXMub3B0aW9ucyk7XG4gICAgfVxuXG4gICAgcmV0dXJuIGNvdW50VXA7XG4gIH1cblxuICBwcml2YXRlIGFuaW1hdGUoKSB7XG4gICAgdGhpcy5jb3VudFVwLnJlc2V0KCk7XG4gICAgaWYgKHRoaXMuZW5kVmFsID4gOTk5KSB7XG4gICAgICB0aGlzLmNvdW50VXAuc3RhcnQoKCkgPT4gdGhpcy5jb3VudFVwLnVwZGF0ZSh0aGlzLmVuZFZhbCkpO1xuICAgICAgc2V0VGltZW91dCgoKSA9PiB7XG4gICAgICAgIHRoaXMuY29tcGxldGUuZW1pdCgpO1xuICAgICAgfSwgdGhpcy5kdXJhdGlvbiAqIDEwMDApO1xuICAgIH0gZWxzZSB7XG4gICAgICB0aGlzLmNvdW50VXAuc3RhcnQoKCkgPT4ge1xuICAgICAgICB0aGlzLmNvbXBsZXRlLmVtaXQoKTtcbiAgICAgIH0pO1xuICAgIH1cbiAgfVxufVxuIiwiaW1wb3J0IHsgTmdNb2R1bGUgfSBmcm9tICdAYW5ndWxhci9jb3JlJztcbmltcG9ydCB7IENvdW50VXBEaXJlY3RpdmUgfSBmcm9tICcuL2NvdW50LXVwLmRpcmVjdGl2ZSc7XG5cbkBOZ01vZHVsZSh7XG4gIGltcG9ydHM6IFtdLFxuICBkZWNsYXJhdGlvbnM6IFtDb3VudFVwRGlyZWN0aXZlXSxcbiAgZXhwb3J0czogW0NvdW50VXBEaXJlY3RpdmVdXG59KVxuZXhwb3J0IGNsYXNzIENvdW50VXBNb2R1bGUgeyB9XG4iXSwibmFtZXMiOlsiRXZlbnRFbWl0dGVyIiwiRGlyZWN0aXZlIiwiRWxlbWVudFJlZiIsIklucHV0IiwiSG9zdExpc3RlbmVyIiwiT3V0cHV0IiwiTmdNb2R1bGUiXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7QUFBQTtRQWdERSwwQkFBb0IsRUFBYztZQUFkLE9BQUUsR0FBRixFQUFFLENBQVk7O1lBRnhCLGFBQVEsR0FBRyxJQUFJQSxpQkFBWSxFQUFRLENBQUM7U0FFUjs7Ozs7O1FBVHRDLGtDQUFPOzs7OztZQURQO2dCQUVFLElBQUksSUFBSSxDQUFDLGdCQUFnQixFQUFFO29CQUN6QixJQUFJLENBQUMsT0FBTyxFQUFFLENBQUM7aUJBQ2hCO2FBQ0Y7Ozs7O1FBT0Qsc0NBQVc7Ozs7WUFBWCxVQUFZLE9BQXNCO2dCQUNoQyxJQUFJLE9BQU8sQ0FBQyxNQUFNLElBQUksT0FBTyxDQUFDLE1BQU0sQ0FBQyxZQUFZLEVBQUU7b0JBQ2pELElBQUksQ0FBQyxPQUFPLEdBQUcsSUFBSSxDQUFDLGFBQWEsRUFBRSxDQUFDO29CQUNwQyxJQUFJLENBQUMsT0FBTyxFQUFFLENBQUM7aUJBQ2hCO2FBQ0Y7Ozs7UUFFTyx3Q0FBYTs7O1lBQXJCOztvQkFDUSxLQUFLLEdBQUcsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDOztvQkFDMUIsUUFBUSxHQUFHLElBQUksQ0FBQyxRQUFRLElBQUksQ0FBQzs7b0JBQzdCLFFBQVEsR0FBRyxJQUFJLENBQUMsUUFBUSxJQUFJLENBQUM7Z0JBRW5DLElBQUksQ0FBQyxJQUFJLENBQUMsUUFBUSxFQUFFO29CQUNsQixJQUFJLENBQUMsUUFBUSxHQUFHLFFBQVEsQ0FBQztpQkFDMUI7OztvQkFHRyxPQUFPLEdBQUcsSUFBSSxPQUFPLENBQUMsSUFBSSxDQUFDLEVBQUUsQ0FBQyxhQUFhLEVBQUUsS0FBSyxFQUFFLElBQUksQ0FBQyxNQUFNLEVBQUUsUUFBUSxFQUFFLFFBQVEsRUFBRSxJQUFJLENBQUMsT0FBTyxDQUFDOztvQkFDaEcsSUFBSSxHQUFHLElBQUksQ0FBQyxHQUFHLENBQUMsSUFBSSxDQUFDLE1BQU0sR0FBRyxLQUFLLENBQUM7O2dCQUUxQyxJQUFJLElBQUksR0FBRyxHQUFHLEVBQUU7O3dCQUNSLEVBQUUsR0FBRyxDQUFDLElBQUksQ0FBQyxNQUFNLEdBQUcsS0FBSyxJQUFJLENBQUMsQ0FBQyxHQUFHLENBQUM7b0JBQ3pDLE9BQU8sR0FBRyxJQUFJLE9BQU8sQ0FBQyxJQUFJLENBQUMsRUFBRSxDQUFDLGFBQWEsRUFBRSxLQUFLLEVBQUUsSUFBSSxDQUFDLE1BQU0sSUFBSSxFQUFFLEdBQUcsR0FBRyxDQUFDLEVBQUUsUUFBUSxFQUFFLFFBQVEsR0FBRyxDQUFDLEVBQUUsSUFBSSxDQUFDLE9BQU8sQ0FBQyxDQUFDO2lCQUNySDtnQkFFRCxPQUFPLE9BQU8sQ0FBQzthQUNoQjs7OztRQUVPLGtDQUFPOzs7WUFBZjtnQkFBQSxpQkFZQztnQkFYQyxJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssRUFBRSxDQUFDO2dCQUNyQixJQUFJLElBQUksQ0FBQyxNQUFNLEdBQUcsR0FBRyxFQUFFO29CQUNyQixJQUFJLENBQUMsT0FBTyxDQUFDLEtBQUssQ0FBQyxjQUFNLE9BQUEsS0FBSSxDQUFDLE9BQU8sQ0FBQyxNQUFNLENBQUMsS0FBSSxDQUFDLE1BQU0sQ0FBQyxHQUFBLENBQUMsQ0FBQztvQkFDM0QsVUFBVSxDQUFDO3dCQUNULEtBQUksQ0FBQyxRQUFRLENBQUMsSUFBSSxFQUFFLENBQUM7cUJBQ3RCLEVBQUUsSUFBSSxDQUFDLFFBQVEsR0FBRyxJQUFJLENBQUMsQ0FBQztpQkFDMUI7cUJBQU07b0JBQ0wsSUFBSSxDQUFDLE9BQU8sQ0FBQyxLQUFLLENBQUM7d0JBQ2pCLEtBQUksQ0FBQyxRQUFRLENBQUMsSUFBSSxFQUFFLENBQUM7cUJBQ3RCLENBQUMsQ0FBQztpQkFDSjthQUNGOztvQkE5RUZDLGNBQVMsU0FBQzt3QkFDVCxRQUFRLEVBQUUsV0FBVztxQkFDdEI7Ozs7d0JBWkNDLGVBQVU7Ozs7OEJBaUJUQyxVQUFLLFNBQUMsU0FBUzsrQkFHZkEsVUFBSzs2QkFHTEEsVUFBSzsrQkFHTEEsVUFBSzsrQkFHTEEsVUFBSzt1Q0FJTEEsVUFBSzs4QkFHTEMsaUJBQVksU0FBQyxPQUFPOytCQVFwQkMsV0FBTTs7UUE2Q1QsdUJBQUM7S0EvRUQ7Ozs7OztBQ1pBO1FBR0E7U0FLOEI7O29CQUw3QkMsYUFBUSxTQUFDO3dCQUNSLE9BQU8sRUFBRSxFQUFFO3dCQUNYLFlBQVksRUFBRSxDQUFDLGdCQUFnQixDQUFDO3dCQUNoQyxPQUFPLEVBQUUsQ0FBQyxnQkFBZ0IsQ0FBQztxQkFDNUI7O1FBQzRCLG9CQUFDO0tBTDlCOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OzsifQ==