package com.ogaclejapan.qiitanium.domain;

import com.ogaclejapan.qiitanium.domain.model.Articles;
import com.ogaclejapan.qiitanium.domain.model.Comments;
import com.ogaclejapan.qiitanium.domain.model.Tags;
import com.ogaclejapan.qiitanium.domain.model.Users;

public interface DomainComponent {

    void injectDomain(Articles instance);

    void injectDomain(Comments instance);

    void injectDomain(Tags instance);

    void injectDomain(Users instance);

}
