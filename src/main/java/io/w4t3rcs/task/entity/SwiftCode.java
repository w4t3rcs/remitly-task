package io.w4t3rcs.task.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "swift_codes")
public class SwiftCode implements Persistable<String>, Serializable {
    @Id
    @NotBlank
    @Length(max = 16)
    private String code;
    @NotBlank
    private String bankName;
    private String address;
    @NotNull
    @Embedded
    private Country country;
    @NotNull
    @Convert(converter = NumericBooleanConverter.class)
    private boolean isHeadquarter;
    @ToString.Exclude
    @Nullable
    @OneToMany
    @JoinTable(
            name = "headquarters_branches",
            joinColumns = @JoinColumn(name = "headquarter_code"),
            inverseJoinColumns = @JoinColumn(name = "branch_code")
    )
    private List<SwiftCode> branches;

    @Override
    public String getId() {
        return this.code;
    }

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SwiftCode swiftCode = (SwiftCode) o;
        return getCode() != null && Objects.equals(getCode(), swiftCode.getCode());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
