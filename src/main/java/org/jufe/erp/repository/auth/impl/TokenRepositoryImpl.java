package org.jufe.erp.repository.auth.impl;

import org.jufe.erp.entity.TokenInfo;
import org.jufe.erp.repository.BaseRepository;
import org.jufe.erp.repository.auth.TokenRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by raomengnan on 16-9-21.
 * 只需用到对Tocken的CRUD方法就行
 */

@Repository
public class TokenRepositoryImpl extends BaseRepository<TokenInfo> implements TokenRepository {
}
