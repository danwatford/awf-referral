package com.foomoo.awf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class to allow submission of referrals to the application.
 */
@Named
@RequestScoped
public class ReferralSubmitter {

    @Inject
    Referral referral;

    public String submit() {
        return "thanks";
    }

}
