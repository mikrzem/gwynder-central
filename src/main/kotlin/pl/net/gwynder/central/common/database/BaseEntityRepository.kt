package pl.net.gwynder.central.common.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseEntityRepository<T : BaseEntity> : JpaRepository<T, Long>