package seedu.gamebook.model.gameentry;

import static java.util.Objects.requireNonNull;
import static seedu.gamebook.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Cash values should be valid floating point numbers between -1,000,000,000.00 and 1,000,000,000.00 with at"
                + " most 2 decimal places";
    private static final double UPPER_BOUND = 1000000000;
    private static final double LOWER_BOUND = -1000000000;
    private static final String CASH_VALUE_FORMAT = "(?<!.)-?[0-9]*(\\.[0-9]{0,2})?(?!.)";

    private static final Amount EMPTY = new Amount();
    private static DecimalFormat df = new DecimalFormat("0.00");
    private double amount;

    protected Amount() {
        amount = 0;
    }

    /**
     * Constructs Amount.
     *
     * @param amount Should be a string representing a double up to 2 decimal places.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        double parsedAmount = Double.parseDouble(amount.strip());

        // Ensures that amount is never -0
        this.amount = parsedAmount == 0 ? 0 : parsedAmount;
    }

    /**
     * Constructs Amount.
     *
     * @param amount
     */
    public Amount(Double amount) {
        requireNonNull(amount);
        this.amount = amount;
    }

    /**
     * Checks whether input string is valid amount.
     *
     * @param cashAmount
     * @return Whether input string is valid amount.
     */
    public static boolean isValidAmount(String cashAmount) {
        if (cashAmount.equals(".")) {
            return false;
        }
        return cashAmount.strip().matches(CASH_VALUE_FORMAT)
            && Double.parseDouble(cashAmount.strip()) <= UPPER_BOUND
            && Double.parseDouble(cashAmount.strip()) >= LOWER_BOUND;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Calculates the difference between this and another {@code Amount}.
     *
     * @param otherAmount
     * @return Difference between this and other {@code Amount}.
     */
    public Amount minus(Amount otherAmount) {
        requireNonNull(otherAmount);
        return new Amount(amount - otherAmount.amount);
    }

    /**
     * Calculates the sum of this and another {@code Amount}.
     *
     * @param otherAmount
     * @return Sum of this and other {@code Amount}.
     */
    public Amount plus(Amount otherAmount) {
        requireNonNull(otherAmount);
        return new Amount(amount + otherAmount.amount);
    }

    /**
     * Creates a string representing this with added prefix.
     *
     * @param prefix
     * @return
     */
    public String addCurrencySymbol(String prefix) {
        requireNonNull(prefix);
        String sign = amount >= 0 ? "+" : "-";
        String value = String.format("%.2f", amount >= 0 ? amount : -amount);
        return String.format("%s %s%s", sign, prefix, value);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }

    /**
     * Formats it the way it was received for testing purposes.
     */
    public String toCommandString() {
        return String.format("%.2f", amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Amount) {
            Amount tmp = (Amount) obj;
            return amount == tmp.amount;
        }
        return false;
    }

}
