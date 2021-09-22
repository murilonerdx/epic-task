package com.murilonerdx.epictask.services.security;

import com.murilonerdx.epictask.entities.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    Usuario getSessionUser(Model mv);
}
