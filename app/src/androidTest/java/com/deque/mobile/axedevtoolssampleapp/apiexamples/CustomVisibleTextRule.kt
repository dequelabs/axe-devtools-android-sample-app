package com.deque.mobile.axedevtoolssampleapp.apiexamples

import com.deque.axe.android.AxeRuleViewHierarchy
import com.deque.axe.android.AxeView
import com.deque.axe.android.constants.AxeImpact
import com.deque.axe.android.constants.AxeStandard
import com.deque.axe.android.constants.AxeStatus
import com.deque.axe.android.wrappers.AxeProps
import com.deque.axe.android.wrappers.RunRuleResult

class CustomVisibleTextRule: AxeRuleViewHierarchy(
    AxeStandard.WCAG_20,
    AxeImpact.MINOR.value,
    "Pass all visible texts",
    false
) {
    override fun collectProps(
        axeView: AxeView,
        axeProps: AxeProps
    ) {
        super.collectProps(axeView, axeProps)
        axeProps[AxeProps.Name.VISIBLE_TEXT] = axeView.text
    }

    override fun isApplicable(axeProps: AxeProps): Boolean {
        val visibleText = axeProps[AxeProps.Name.VISIBLE_TEXT] as String?
        return visibleText != null && super.isApplicable(axeProps)
    }

    override fun runRule(axeProps: AxeProps): RunRuleResult {
        val runRuleResult = super.runRule(axeProps)
        if (runRuleResult.status.isNotEmpty()) {
            return runRuleResult
        }

        val visibleText = axeProps[AxeProps.Name.VISIBLE_TEXT] as String?

        visibleText?.let {
            return when (it.isNotEmpty()) {
                true -> RunRuleResult(AxeStatus.PASS, "All visible texts are present")
                false -> RunRuleResult(AxeStatus.FAIL, "All visible texts are not present")
            }
        }

        return RunRuleResult(AxeStatus.INCOMPLETE, "Need review")
    }
}