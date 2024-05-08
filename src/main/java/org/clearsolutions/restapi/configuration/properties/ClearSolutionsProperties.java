package org.clearsolutions.restapi.configuration.properties;

import lombok.Getter;
import org.clearsolutions.restapi.configuration.properties.enums.ClearSolutionsApplicationMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "clearsolutions")
@Getter
public class ClearSolutionsProperties {
    /**
     * Defines the application mode.
     */
    private final ClearSolutionsApplicationMode mode;

    /**
     * User properties in the application.
     */
    private final User user;

    @ConstructorBinding
    public ClearSolutionsProperties(ClearSolutionsApplicationMode mode, User user) {
        this.mode = mode;
        this.user = user;
    }

    @Getter
    public static class User {
        /**
         * Min of the user age.
         */
        private final Integer ageMin;

        @ConstructorBinding
        public User(Integer ageMin) {
            this.ageMin = ageMin;
        }
    }
}
